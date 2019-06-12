import React, {Component} from 'react';
import US108 from './US108'
import US109 from './US109'
import US105 from "./US105";
import US108Redux from "./US108/US108Redux";

class HouseConfiguration extends Component {
  constructor(props) {
    super(props);
    this.toggle = this.toggle.bind(this);
    this.state = {collapse: false};
  }

  toggle() {
    this.setState(state => ({collapse: !state.collapse}));
  }

  render() {
    return (
      <div>
        <h2>Welcome to the House Configuration Menu.</h2>
        <h4>Please select the option you want to run.</h4>
        <US108Redux/>
        <US105/>
        <US109/>
      </div>
    );
  }
}

export default HouseConfiguration;
