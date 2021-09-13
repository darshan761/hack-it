import React from "react";
import http from "../../Service/http-common-service-call";
import "./HomePage.css";
import header from "../../images/hack-it.png";
import { ToastContainer, toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import SelectionSection from "../SelectionSection/SelectionSection";
import UserForm from "../UserForm/UserForm";
import "react-loader-spinner/dist/loader/css/react-spinner-loader.css";
import Loader from "react-loader-spinner";


export default class HomePage extends React.Component {
  constructor(props) {
    super(props);

    // Set initial state
    this.state = {
      seats: [],
      rowToSeatMap: {},
      availableSeats: [],
      selectedSeats: [],
      showPopup: false,
      isLoading: true
    };
  }

  componentDidMount() {
    http
      .get("/seats")
      .then((res) => {
        const seats = res.data;
        console.log(seats);
        const availableSeats = seats;
        // const availableSeats = placeholder.filter(seats => (seats.status = 'AVAILABLE'))
        const rowToSeatMap = {};
        seats.forEach((seat) => {
          let arr = [];
          if (rowToSeatMap[seat.rowName]) {
            arr = rowToSeatMap[seat.rowName];
          }
          arr.push(seat);
          rowToSeatMap[seat.rowName] = arr;
        });

        this.setState({ seats, rowToSeatMap, availableSeats });
        this.setState({
          isLoading: false
        });
        // console.log(this.state.rowToSeatMap)
      })
      .catch((error) => {
        console.log(error);
      });
  }

  onClickData(seat) {
    if (this.state.selectedSeats.indexOf(seat) > -1) {
      this.setState({
        availableSeats: this.state.availableSeats.concat(seat),
        selectedSeats: this.state.selectedSeats.filter((res) => res !== seat),
      });
    } else {
      if (this.state.selectedSeats.length > 7) {
        toast("Cannot select more than 8 seats");
      } else {
        this.setState({
          selectedSeats: this.state.selectedSeats.concat(seat),
          availableSeats: this.state.availableSeats.filter(
            (res) => res !== seat
          ),
        });
      }
    }
  }

  clearSelection() {
    this.setState({
      selectedSeats: [],
    });
  }

  bookSeat() {
    if (this.state.selectedSeats.length === 0) {
      alert("Please select a seat!");
    } else {
      alert("Seat Booked Successfully!");
    }
  }

  togglePopup() {
    this.setState({
      showPopup: !this.state.showPopup,
    });
  }
  reserved(seat) {
    toast(
      seat.rowName + seat.number + " is already booked! select another seat."
    );
  }

  render() {
    return (
      <div>
        <div className="container">
          <ToastContainer />
          <div className="header">
            <img src={header} alt="hack-it" />
            <hr />
          </div>
          <div className="row">
            <div className="col-sm-7">
              <div className="screen">
                <center>SCREEN</center>
                <hr />
              </div>
              { this.state.isLoading === true ?
              <center>
                <Loader
                type="TailSpin"
                color="#E10512"
                height={80} 
                width={80}
              />
              </center> 
              :
              Object.keys(this.state.rowToSeatMap).map((key) => (
                <div className="row row-inner">
                  <div className="seat-row">{key}</div>
                  {this.state.rowToSeatMap[key].map((seat) =>
                    seat.status === "AVAILABLE" ? (
                      <div
                        className={
                          this.state.selectedSeats.indexOf(seat) > -1
                            ? "seat-selected"
                            : "seat-available"
                        }
                        key={seat.seatId}
                        onClick={(e) => this.onClickData(seat)}
                      >
                        {seat.rowName}
                        {seat.number}{" "}
                      </div>
                    ) : (
                      <div
                        className="seat-occupied"
                        key={seat.seatId}
                        onClick={(e) => this.reserved(seat)}
                      >
                        {seat.rowName}
                        {seat.number}{" "}
                      </div>
                    )
                  )}
                </div>
              ))}
              <div className="legend">
                <hr />
                <div className="row row-inner">
                  <div className="seat-available" /> AVAILABLE
                </div>
                <div className="row row-inner">
                  <div className="seat-selected" /> SELECTED
                </div>
                <div className="row row-inner">
                  <div className="seat-occupied" /> OCCUPIED
                </div>
                <hr />
              </div>
            </div>
            <div className="col-sm-5">
              <SelectionSection selected={this.state.selectedSeats} />
              <hr />
              <button
                disabled={this.state.selectedSeats.length === 0}
                onClick={this.clearSelection.bind(this)}
              >
                CLEAR
              </button>
              <button
                disabled={this.state.selectedSeats.length === 0}
                onClick={this.togglePopup.bind(this)}
              >
                BOOK
              </button>

              {this.state.showPopup ? (
                <UserForm
                  selected={this.state.selectedSeats}
                  closePopup={this.togglePopup.bind(this)}
                />
              ) : null}
            </div>
          </div>
        </div>
      </div>
    );
  }
}
