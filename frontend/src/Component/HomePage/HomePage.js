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

/**
 * HomePage component for displaying seats present and boooking.
 */
export default class HomePage extends React.Component {
  constructor(props) {
    super(props);

    // Set initial state
    this.state = {
      
      seats: [], /** for all thee seats present */
      rowToSeatMap: {}, /** for diaplying in the grid */
      availableSeats: [], /** seats that are not BOOKED */
      selectedSeats: [], /** user selected seats */
      showPopup: false, /** for showing user submit form */
      isLoading: true /** for spinner status */
    };
  }

  /**
   * Fetching seats info from the API
   */
  componentDidMount() {
    http
      .get("/seats")
      .then((res) => {
        const seats = res.data;
        console.log(seats);
        const availableSeats = seats;
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
      })
      .catch((error) => {
        console.log(error);
      });
  }

/**
 * when clicked on the seat
 * Updating state as per selection of seats
 * @param {*} seat selected seat
 */
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

  /**
   * clear all the selected seats
   */
  clearSelection() {
    this.setState({
      selectedSeats: [],
    });
  }

  /**
   * for displaying userform on popup
   */
  togglePopup() {
    this.setState({
      showPopup: !this.state.showPopup,
    });
  }

  /**
   * If the seat is already BOOKED
   * @param {*} seat selected seat
   */
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
                        onClick={(e) => this.onClickData(seat)}>
                        {seat.rowName}
                        {seat.number}{" "}
                      </div>
                    ) : (
                      <div
                        className="seat-occupied"
                        key={seat.seatId}
                        onClick={(e) => this.reserved(seat)}>
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
                onClick={this.clearSelection.bind(this)}>
                CLEAR
              </button>
              <button
                disabled={this.state.selectedSeats.length === 0}
                onClick={this.togglePopup.bind(this)}>
                BOOK
              </button>

              {this.state.showPopup ? (
                <UserForm
                  selected={this.state.selectedSeats}
                  closePopup={this.togglePopup.bind(this)}/>
              ) : null}
            </div>
          </div>
        </div>
      </div>
    );
  }
}
