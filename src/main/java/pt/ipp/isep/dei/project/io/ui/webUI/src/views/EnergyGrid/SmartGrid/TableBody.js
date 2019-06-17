import React, {Component} from 'react';
import TableHeader from "./TableHeader";
import {Button} from "reactstrap";
import RemoveFromGrid from "./gridRedux/RemoveFromGrid"
import {deleteRoomFromGrid} from "../US149/Actions";
import connect from "react-redux/es/connect/connect";

class TableBody extends Component {

  constructor(props) {
    super(props);
    this.state = {
      item: [],
      grid: '',
    }
  }

  componentDidMount() {
    const token = localStorage.getItem('loginToken');
    fetch('https://localhost:8443/gridSettings/grids/' + this.props.grid, {
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
          item: json,
        })
      })
      .catch(console.log)
  }

  render() {
    const headers = {
      name: "Rooms",
      floor: "Floor",
      height: "Height (m)",
      length: "Length (m)",
      width: "Width (m)",
      remove: "Remove from grid",
    };
    var {item} = this.state;
    return (
      <>
        <TableHeader headers={headers}/>
        {item.map(item => (
          <tr key={item.name}>
            <td style={{
              textAlign: "center"
            }}> {item.name}</td>
            <td style={{
              textAlign: "center"
            }}>{item.floor} </td>
            <td style={{
              textAlign: "center"
            }}> {item.height}</td>
            <td style={{
              textAlign: "center"
            }}> {item.length} </td>
            <td style={{
              textAlign: "center"
            }}> {item.width} </td>
            <td style={{
              textAlign: "center"
            }}>
              <RemoveFromGrid link={item.href} grid={this.props.grid} name={item.name}/>
            </td>
          </tr>
        ))}
      </>
    );
  }

}


const mapDispatchToProps = (dispatch) => {
  return {
    onDeleteRoomFromGrid: (name, grid) => {
      dispatch(deleteRoomFromGrid({name, grid}))
    }
  }
};

export default connect(null, mapDispatchToProps)(TableBody);
