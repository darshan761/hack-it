import React from "react";

/**
 * Selection Section for displaying the selected seats
 */
export default class SelectionSection extends React.Component {
  render() {
    return (
      <div className="right">
        <h4> WHERE TO SIT? </h4>
        <hr />
        {this.props.selected.length === 0 ? (
          <h6>No seat selected</h6>
        ) : (
          this.props.selected.map((seat) => (
            <div className="seat-selected" key={seat}>
              {seat.rowName}
              {seat.number}
            </div>
          ))
        )}

        <hr />
        <div>
          <div className="ticket-details">
            {" "}
            <i className="fa fa-ticket" aria-hidden="true"></i> x{this.props.selected.length}
          </div>
          <div className="ticket-details">
            {" "}
            <i className="fa fa-usd" aria-hidden="true"></i>{" "}
            {this.props.selected.reduce(
              (acc, seat) => (acc = acc + seat.price), 0 )}{" "}
          </div>
        </div>
      </div>
    );
  }
}
