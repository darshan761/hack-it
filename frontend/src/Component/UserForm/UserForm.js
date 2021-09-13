import React from "react";
import http from "../../Service/http-common-service-call";
import { toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";

export default class UserForm extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      name: "",
      email: "",
      mobileNumber: "",
    };

    this.handleChange = this.handleChange.bind(this);
    this.handleSubmit = this.handleSubmit.bind(this);
  }
  handleChange(event) {
    let nam = event.target.name;
    let val = event.target.value;
    this.setState({
      [nam]: val,
    });
  }

  handleSubmit(event) {
    let req = { user: this.state, seats: this.props.selected };
    this.refs.book.setAttribute("disabled", "disabled");
    http.post("/book", req).catch((res) => {
      toast("Error:" + res.response.data);
      console.log(res.response);
    });

    toast("Booked " + this.props.selected.length + " ticket(s) successfully");
    toast("Sent email to " + this.state.email);
    event.preventDefault();
    setTimeout(() => window.location.reload(), 5000);
  }

  render() {
    return (
      <div className="popup">
        <div className="popup_inner">
          <h3> Enter Details </h3>
          <form onSubmit={this.handleSubmit}>
            <div className="form-group">
              <input
                type="text"
                name="name"
                minlength="4"
                placeholder="Name"
                required
                onChange={this.handleChange}
              />
            </div>
            <div className="form-group">
              <input
                type="email"
                name="email"
                placeholder="Email"
                required
                onChange={this.handleChange}
              />
            </div>
            <div className="form-group">
              <input
                type="tel"
                name="mobileNumber"
                pattern="^\d{10}$"
                placeholder="10 digits Mobile No"
                required
                onChange={this.handleChange}
              />
            </div>
            <div className="form-group">
              <input
                ref="book"
                className="btn btn-primary"
                type="submit"
                value="BOOK"
              />
            </div>
            <div className="form-group">
              <input
                className="btn btn-primary"
                onClick={this.props.closePopup}
                value="BACK"
              />
            </div>
          </form>
        </div>
      </div>
    );
  }
}
