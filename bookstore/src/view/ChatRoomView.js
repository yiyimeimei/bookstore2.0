import React from 'react';
import {Layout, Col, Row} from 'antd'
import {HeaderInfo} from "../components/HeaderInfo";
import {withRouter} from "react-router-dom";
import CartList from "../components/CartList";
import Footer from "../components/Footer";
import ChatRoomTable from "../components/chatroom/ChatRoomTable";

class ChatRoomView extends React.Component{

    constructor(props) {
        super(props);
    }

    /*componentDidMount(){
        let user = localStorage.getItem("user");
        this.setState({user:user});

        const query = this.props.location.search;
        const arr = query.split('&');
        const bookId = arr[0].substr(4);
        getBook(bookId, (data) => {this.setState({bookInfo: data})})
    }*/

    render(){
        return(
            <div>
                <HeaderInfo/>
                <Row>
                    <Col offset={10} span={4}>
                        <h1>在线聊天</h1>
                    </Col>
                </Row>


                <Layout >
                    <Col offset={4} span={16}>
                        <ChatRoomTable />
                    </Col>
                </Layout>
                <br/>
                <Footer/>
            </div>
        );
    }
}

export default withRouter(ChatRoomView);