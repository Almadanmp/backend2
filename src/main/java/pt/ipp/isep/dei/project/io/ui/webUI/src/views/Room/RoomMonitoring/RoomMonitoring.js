import React, { Component } from 'react';
import {Collapse, Button, CardBody, Card, Col, Row} from 'reactstrap';

import US610 from "./US610/US610";
import US605 from "./US605/US605";

class RoomMonitoring extends Component {
  constructor(props) {
    super(props);
    this.toggle = this.toggle.bind(this);
    this.state = { collapse: false };
  }

  toggle() {
    this.setState(state => ({ collapse: !state.collapse }));
  }

  render() {
    return (
      <div>
        <h2>Welcome to the Room Monitoring Menu.</h2>
        <h4>Please select the option you want to run.</h4>
        <Row>
<Col  xs="6" sm="4" md="4">
        <US605/>
</Col>
          <Col  xs="6" sm="4" md="4">
        <US610/>
          </Col>
        </Row>
      </div>
    );
  }
}

export default RoomMonitoring;
