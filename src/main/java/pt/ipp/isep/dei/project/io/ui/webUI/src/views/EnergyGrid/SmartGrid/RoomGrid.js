import React, {Component} from 'react';
import {Button, Card, CardBody, CardHeader, Col, Form, FormGroup, Input, Label, Table} from "reactstrap";
import US147 from "./AttachRoom/US147";
import TableBody from "./TableBody";

class RoomGrid extends Component {

  constructor(props) {
    super(props);
    this.state = {
      item: [],
      isLoaded: true,
      value: ''
    };
    this.handleChange = this.handleChange.bind(this);
  }

  componentDidMount() {
    const token = localStorage.getItem('loginToken');
    fetch('https://localhost:8443/gridSettings/grids', {
      headers: {
        'Authorization': token,
        "Access-Control-Allow-Credentials": true,
        "Access-Control-Allow-Origin": "*",
        "Content-Type": "application/json"
      }
    })
      .then(res => res.json())
      .then((json) => {
        this.setState({
          isLoaded: true,
          item: json,
        })
      })
      .catch(console.log)
  }

  handleChange(event) {
    this.setState({value: event.target.value});
  }


  render() {

    var {isLoaded, item} = this.state;
    if (!isLoaded) {
      return <div>Loading
        ...</div>
    } else {
      if (!item.error) {
        return (

          <>
            {item.map(items => (
              <Col xs="12" lg="6">
                <Card value={items.name} key={items.name}>
                  <CardHeader>
                    <h5>Grid: {items.name}</h5>
                  </CardHeader>
                  <CardBody style={{
                    textAlign: "right"
                  }}>
                    <Table responsive>
                      <TableBody gridID={items.name} roomID={item.value}/>

                    </Table>
                    <US147 grid={items.name}/>
                  </CardBody>
                </Card>
              </Col>
            ))}
          </>
        );
      } else {
        return null
      }
    }
  }
}

export default RoomGrid;
