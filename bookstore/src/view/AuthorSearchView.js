import React from 'react'
import {Col, Row} from "antd";
import Search from "antd/es/input/Search";
import {getBooks, searchBookByDescription} from "../services/bookService";
import {getAuthorByTitle} from "../services/microService";
import {HeaderInfo} from "../components/HeaderInfo";

class AuthorSearchView extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            book: "",
        };
    }

    renderSearchBar = () =>{
        return(
            <Row align={"center"}>
                <Col offset={6} span={12} style={{margin: '20px'}}>
                    <Search
                        placeholder="请输入书籍全称"
                        allowClear
                        enterButton="查找"
                        size="large"
                        onSearch={this.onSearch}
                    />
                </Col>
            </Row>
        )
    }

    renderAuthor = () => {
        return(
                <div>
                    <p>作者名称为：{this.state.book.author}</p>
                </div>
            )

    }

    onSearch = (value) => {
        console.log("search");
        let keyword = value.toLowerCase();
        getAuthorByTitle(keyword, this.handleSearchResult);
    };

    handleSearchResult = data => {
        console.log(data);
        this.setState({
            book: data,
        });
    };

    render = () => {
        return(
            <div>
                <HeaderInfo/>
                <br/>

                <Col offset={8} span={8}>
                    {this.renderSearchBar()}
                </Col>
                <Col offset={8} span={8}>
                    {this.renderAuthor()}
                </Col>

            </div>
        )
    }

}
export  default  AuthorSearchView;