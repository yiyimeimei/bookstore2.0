import React from 'react';
import {Button, Input, Row, Col} from 'antd';
import {CheckOutlined, UserOutlined} from '@ant-design/icons';
import {getBooks} from "../../services/bookService";
import TextArea from "antd/es/input/TextArea";

let wsocket = new WebSocket("ws://localhost:8080/websocketbot");

class ChatRoomTable extends React.Component{

    constructor(props) {
        super(props);
        this.state = {
            username: '',
            /*wsocket: new WebSocket("ws://localhost:8080/websocketbot"),
            chatText: '',
            wsconsole: {},*/
            userListValue: '',
            textareaValue: '',
            showConsole: false,
            nameInputDisabled: false,
            joinButtonDisabled: false,
            textInputDisabled: true,
            messageInputValue:'',
        };
    }

    componentDidMount() {
        wsocket.onmessage = this.onMessage;
        window.addEventListener("load", this.connect, false);
    }

    onMessage = (event) =>
    {
        let line = "";
        /* Parse the message into a JavaScript object */
        let msg = JSON.parse(event.data);
        if (msg.type === "chat")
        {
            line = msg.name + ": ";
            console.log("msg.target " + msg.target);
            if (msg.target.length > 0 && msg.target !== " ")
                line += "@" + msg.target + " ";
            line += msg.message + "\n";
            /* Update the chat area */
            let oldValue = this.state.textareaValue;
            this.setState({
                textareaValue: oldValue + line,
            })

        }
        else if (msg.type === "info")
        {
            line = "[--" + msg.info + "--]\n";
            /* Update the chat area */
            let oldValue = this.state.textareaValue;
            this.setState({
                textareaValue: oldValue + line,
            })
        }
        else if (msg.type === "users")
        {
            line = "Users:\n";
            for (let i = 0; i < msg.userlist.length; i++)
                line += "-" + msg.userlist[i] + "\n";
            /* Update the user list area */
            this.setState({
                userListValue: line,
            })
        }
        let ta = document.getElementById("messageDisplay");
        ta.scrollTop = 999999;

        /* Update the Websocket console area */
        /*wsconsole.value += "-> " +  evt.data + "\n";
        wsconsole.scrollTop = 999999;*/
    }

    connect() {
        this.setState({
            //wsconsole
        })
        //wsconsole = document.getElementById("wsconsole");
        //wsocket = new WebSocket("ws://localhost:8080/bookstore_backend_war/websocketbot");
        //wsocket = new WebSocket("ws://localhost:8080/websocketbot");
        //wsocket.onmessage = onMessage;
        document.getElementById("username").focus();
        //document.getElementById("consolediv").style.visibility = 'hidden';
    }

    renderNameInput = () =>
    {
        return(
            <div>
                {/*<p>请输入您想使用的昵称：</p>*/}
                <Row>
                    <Col offset={6} span={6}>
                        <Input size={"large"}
                               id={"username"}
                               placeholder="请输入您想使用的昵称"
                               prefix={<UserOutlined />}
                               onChange={this.handleSetUserName.bind(this)}
                               disabled={this.state.nameInputDisabled}/>
                    </Col>
                    <Col offset={1} span={8}>
                        <Button type="submit" icon="qq" size={"large"} onClick={this.handleJoin.bind(this)} disabled={this.state.joinButtonDisabled}>
                            点击加入聊天
                        </Button>
                    </Col>
                </Row>


            </div>
        );
    }
    renderMessageInput = () => {
        return(
            <div>
                <Input size={"large"}
                       id={"messageInput"}
                       placeholder="请输入聊天内容"
                       value={this.state.messageInputValue}
                       onChange={this.handleMessageOnChange}
                       onPressEnter={this.handleSendNewMessage.bind(this)}
                       disabled={this.state.textInputDisabled}
                       width={90}
                       height={5}/>
            </div>
        );
    }

    renderAllMessage = () => {
        return(
            <div>
                <Col span={18}>
                    <TextArea readOnly={true}
                              id="messageDisplay"
                              value={this.state.textareaValue}
                              /*cols={5}*/
                              rows={20}>
                    </TextArea>
                </Col>

            </div>
        )
    }

    renderUserList = () => {
        return(
            <div>
                <Col offset={1} span={5}>
                    <TextArea readOnly={true}
                              id="userListDisplay"
                              value={this.state.userListValue}
                              cols={20}
                              rows={20}>
                    </TextArea>
                </Col>

            </div>
        )
    }

    handleSetUserName = (event) => {
        this.setState({
            username: event.target.value,
        });
    }

    handleJoin = () => {
        //console.log("username" + this.state.username);
        let name = this.state.username;
        if(name.length > 0 && name[0] !== " ")
        {
            let joinMsg = {};
            joinMsg.type = "join";
            joinMsg.name = name;
            let jsonstr = JSON.stringify(joinMsg);
            wsocket.send(jsonstr);
            this.setState({
                nameInputDisabled: true,
                joinButtonDisabled: true,
                textInputDisabled: false,
            })
        }
    }



    handleSendNewMessage = (event) => {
        let messageInput = event.target.value;
        if(messageInput.length > 0)
        {
            let chatMsg = {};
            chatMsg.type = "chat";
            chatMsg.name = this.state.username;
            chatMsg.target = this.getTarget(messageInput.replace(/,/g, ""));
            chatMsg.message = this.cleanTarget(messageInput);
            chatMsg.message = chatMsg.message.replace(/(\r\n|\n|\r)/gm,"");
            let jsonstr = JSON.stringify(chatMsg);
            wsocket.send(jsonstr);
            this.setState({
                messageInputValue: '',
            })

            /*document.getElementById("messageInput").value = "iji";
            this.setState({
                messageInputValue: "",
            })
            console.log(document.getElementById("messageInput").value);*/
            //messageInput.current.setValue("");
            //document.getElementById("messageInput").value = "";
            //event.target.value = "";

            /*let newconsole = this.state.wsconsole.value + "<- " + jsonstr + "\n";
            this.setState(
                {
                    wsconsole.value = newconsole,
                }
            );*/
        }
    }

    handleMessageOnChange = (event) => {
        this.setState({
            messageInputValue: event.target.value,
        })
    }

    getTarget = (str) => {
        let array = str.split(" ");
        let target = " ";
        for (let i = 0; i < array.length; i++)
        {
            if(array[i].charAt(0) === '@')
            {
                target = array[i].substring(1, array[i].length);
                target = target.replace(/(\r\n|\n|\r)/gm,"");
            }
        }
        return target;
    }

    cleanTarget = (str) => {
        let array = str.split(" ");
        let cleanstr = "";
        for(let i = 0; i < array.length; i++)
        {
            if(array[i].charAt(0) !== '@')
            {
                cleanstr += array[i] + " ";
            }
        }
        return cleanstr.substring(0, cleanstr.length - 1);
    }

    render() {
        return(
            <div>
                {this.renderNameInput()}
                <br/>
                {this.renderMessageInput()}
                <br/>
                {this.renderAllMessage()}
                {this.renderUserList()}
            </div>

        );
    }


}
export default ChatRoomTable;