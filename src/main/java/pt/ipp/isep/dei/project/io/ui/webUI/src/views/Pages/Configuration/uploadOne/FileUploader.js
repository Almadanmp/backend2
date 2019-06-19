import React, {Component} from 'react';
import Dropzone from 'react-dropzone'
import {uploadFile} from "./UploadActions";
import {Button} from "reactstrap";
import {connect} from "react-redux";

class FileUploader extends Component {
  constructor(props) {
    super(props);
    this.state = {file: null};
    this.onDrop = this.onDrop.bind(this);
    this.handleSubmit = this.handleSubmit.bind(this);
  }

  onDrop = (acceptedFiles) => {
    console.log(acceptedFiles);
    this.setState({file: acceptedFiles})
  }


  handleSubmit() {
    this.props.onPostFile(this.state);
  }

  render() {
    const maxSize = 1048576;
    return (
      <div className="text-center mt-5">
        <Dropzone
          onDrop={this.onDrop}
          accept="application/json, text/csv, text/xml"
          minSize={0}
          maxSize={maxSize}
        >
          {({getRootProps, getInputProps, isDragActive, isDragReject, rejectedFiles}) => {
            const isFileTooLarge = rejectedFiles.length > 0 && rejectedFiles[0].size > maxSize;
            return (
              <div {...getRootProps()}>
                <input {...getInputProps()} />
                {!isDragActive && 'Click here or drop a file to upload!'}
                {isDragActive && !isDragReject && "Drop it like it's hot!"}
                {isDragReject && "File type not accepted, sorry!"}
                {isFileTooLarge && (
                  <div className="text-danger mt-2">
                    File is too large.
                  </div>
                )}
              </div>

            )
          }
          }
        </Dropzone>
        <p></p>
        <p></p>
        <p></p>
        <Button style={{backgroundColor: '#e4e5e6', marginBottom: '1rem'}} onClick={this.handleSubmit}>Submit</Button>
      </div>

    );
  }
}

const mapDispatchToProps = (dispatch) => {
  return {
    onPostFile: (file) => {
      dispatch(uploadFile({file}))
    }
  }
};

export default connect(
  null,
  mapDispatchToProps
)(FileUploader);
