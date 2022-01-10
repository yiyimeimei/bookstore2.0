import React from 'react';
import {Menu, Dropdown, Col, List, Row} from 'antd'
import { DownOutlined } from '@ant-design/icons';
import {getBooks, getBooksByKeyword, getBooksByType, searchBookByDescription} from "../services/bookService";
import DisplayCard from "./DisplayCard";
import Search from "antd/es/input/Search";
export class BookList extends React.Component{

    constructor(props) {
        super(props);
        this.state = {
            books: []
        };
    }

    componentDidMount() {
        const callback =  (data) => {
           this.setState({books:data});
        };
        getBooks(callback);
    }

    renderSearchBar = () => {
        const menu = (
            <Menu onClick={this.onSearch3}>
                <Menu.Item key="编程">编程</Menu.Item>
                <Menu.Item key="同性爱情">同性爱情</Menu.Item>
                <Menu.Item key="日系推理">日系推理</Menu.Item>
                <Menu.Item key="欧美推理">欧美推理</Menu.Item>
                <Menu.Item key="日系短篇小说">日系短篇小说</Menu.Item>
                <Menu.Item key="日系小说">日系小说</Menu.Item>
                <Menu.Item key="馆系列">馆系列</Menu.Item>
            </Menu>
        );
        return (
            <div>
                <Row align={"center"}>
                    <Col offset={6} span={12} style={{margin: '20px'}}>
                        <Search
                            placeholder="请输入检索词"
                            allowClear
                            enterButton="查找"
                            size="large"
                            onSearch={this.onSearch}
                        />
                    </Col>
                </Row>
                <Row align={"center"}>
                    <Col offset={6} span={12} style={{margin: '20px'}}>
                        <Search
                            placeholder="请输入书籍描述关键词"
                            allowClear
                            enterButton="查找"
                            size="large"
                            onSearch={this.onSearch2}
                        />
                    </Col>
                </Row>
                <Row align={"center"}>
                    <Dropdown overlay={menu}>
                        <a className="ant-dropdown-link" onClick={e => e.preventDefault()}>
                            按照标签搜索图书<DownOutlined />
                        </a>
                    </Dropdown>
                </Row>
            </div>
        );
    };

    onSearch = (value) => {
        let keyword = value.toLowerCase();
        getBooksByKeyword(keyword, this.handleSearchResults);
    };

    onSearch2 = (value) => {
        let keyword = value.toLowerCase();
        searchBookByDescription(keyword, this.handleSearchResults);
    };

    onSearch3 = ({ key }) => {
        console.log(key);
        getBooksByType(key, this.handleSearchResults);

    };

    handleSearchResults = books => {
        /*this.preHandleBooks(books);*/
        this.setState({
            books: books,
        });
    };

    render() {
        return (
        <List
            size="large"
            grid={{gutter: 10, column: 3}}
            dataSource={this.state.books}
            header={this.renderSearchBar()}
            pagination={{
                onChange: page => {
                    console.log(page);
                },
                pageSize: 6,

            }}
            itemLayout="horizontal"
            renderItem={item => (
                <List.Item>
                    <DisplayCard info={item} />
                </List.Item>
            )}
        />
        );
    }

}