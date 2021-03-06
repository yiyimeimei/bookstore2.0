import React from 'react';
import WrappedLoginForm from '../components/LoginForm';
import '../css/loginPage.css';
import '../css/login.css';
import 'antd/dist/antd.css';
import {Col, Divider, Row} from "antd";
import Footer from "../components/Footer";
import {withRouter} from "react-router-dom";
import StepLine1 from "../components/StepLine";
import OrderTable from "../components/OrderTable";
import MyOrderList from "../components/MyOrderList";
import {HeaderInfo} from "../components/HeaderInfo";
import BackTop from "../components/BackTop";


class MyOrderListView extends React.Component{


    render(){
        return(
            <div>
                <HeaderInfo />
                <MyOrderList />
                <br/>
                {/*<Footer />*/}
                <BackTop />
            </div>
        );

    }
}
export default withRouter(MyOrderListView);