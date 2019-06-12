import React, {Component} from 'react';
import {connect} from 'react-redux';
import {fetchGAs} from './Actions108';
import {Alert, Card, CardBody, Col, Row, Table} from "reactstrap";
import TableHeaderUS108 from "./TableHeaderUS108";
import TableBodyUS108 from "./TableBodyUS108"

class US108Redux extends Component {
  constructor(props) {
    super(props);
  }

  componentDidMount() {
    this.props.onFetchUsers();
  }

  render() {
    const headers = {
      name: "Name",
      floor: "Floor",
      height: "Height (m)",
      length: "Length (m)",
      width: "Width (m)",
      edit: "Configure"
    };

    const {loading, data} = this.props;
    if (loading === true) {
      return (<h1>Loading ....</h1>);
    }
    if (data.length >0) {{
      return (
        <div className="animated fadeIn">
          <Row>
            <Col xs="12" lg="6">
              <Card>
                <CardBody>
                  <Table responsive>
                    <TableHeaderUS108 headers={headers}/>
                    <TableBodyUS108 data={data}/>
                  </Table>
                </CardBody>
              </Card>
            </Col>
          </Row>
        </div>
      );
    }}
     else {
       return(
         <div className="help-block"><Alert color="warning">No rooms on the house</Alert></div>
       )

    }
  }
}

const mapStateToProps = (state) => {
  return {
    loading: state.Reducer108.loading,
    data: state.Reducer108.data,
    error: state.Reducer108.error
  }
};

const mapDispatchToProps = (dispatch) => {
  return {
    onFetchUsers: () => {
      dispatch(fetchGAs())
    }

  }
};

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(US108Redux);
