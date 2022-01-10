import React from 'react';
import {Button, Input, Row, Col} from 'antd';
import {visitHomePage} from "../../services/visitService";

class VisitNumberDisplayer extends React.Component
{
    constructor(props) {
        super(props);
        this.state = {
            visit: '',
        };
    }
    componentDidMount() {
        const callback =  (data) => {
            this.setState({visit:data});
        };
        visitHomePage(callback);
    }

    render() {
        return(
          <div>
              <Col>
                  <h3>累计访问量：{this.state.visit}</h3>
              </Col>
          </div>
        );
    }


}
export default VisitNumberDisplayer;