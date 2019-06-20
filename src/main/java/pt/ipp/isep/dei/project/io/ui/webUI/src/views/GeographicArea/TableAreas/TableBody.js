import React, {Component} from 'react';
import TableHeader from "./TableHeader";
import connect from "react-redux/es/connect/connect";

class TableBody extends Component {

  constructor(props) {
    super(props);
    this.state = {
      item: [],
    }
  }

  componentDidMount() {
    const token = localStorage.getItem('loginToken');
    fetch('https://localhost:8443/geoAreas/', {
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
    console.log(this.state.item);

  }

  render() {
    const headers = {
      name: "Name",
      type: "Type",
      description: "Description",
      sensors: "Sensors",
      children: "Child Areas",
    };
    var {item} = this.state;
    return (
      <>
        <TableHeader headers={headers}/>
        {item.map(item => (
          <tr key={item.id}>
            <td style={{
              textAlign: "center"
            }}> {item.name}</td>
            <td style={{
              textAlign: "center"
            }}>{item.typeArea} </td>
            <td style={{
              textAlign: "center"
            }}>{item.description} </td>
            <td style={{
              textAlign: "center"
            }}>
              {/*<GetSensors link={item.links.map(hrefs => (hrefs.rel.indexOf("1.")!=-1 ? hrefs.href : "No link available"))} grid={this.props.grid} name={item.name}/>*/}
              {/*<GetChildren link={item.links.map(hrefs => (hrefs.rel.indexOf("1.")!=-1 ? hrefs.href : "No link available"))} grid={this.props.grid} name={item.name}/>*/}
            </td>
          </tr>
        ))}
      </>
    );
  }

}



export default TableBody;
