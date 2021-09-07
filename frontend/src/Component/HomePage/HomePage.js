import React from 'react';
import http from '../../Service/http-common-service-call'
import './HomePage.css'

export default class HomePage extends React.Component {
  
  constructor(props){ 
    super(props) 
        
    // Set initial state 
    this.state = {
      seats: [],
      rowToSeatMap: {},
      availableSeats: [],
      selectedSeats: [],
      showPopup: false
    }
  } 

  componentDidMount() {
    http.get('/seats')
      .then(res => {
        const seats = res.data; 
        console.log(seats)
        const availableSeats = seats
        // const availableSeats = placeholder.filter(seats => (seats.status = 'AVAILABLE'))
        const rowToSeatMap = {}
        seats.forEach(seat => {
            let arr =[]
            if(rowToSeatMap[seat.rowName]){
                arr = rowToSeatMap[seat.rowName]   
            }
            arr.push(seat)
            rowToSeatMap[seat.rowName] = arr
        });
        
        this.setState({ seats, rowToSeatMap, availableSeats });
        // console.log(this.state.rowToSeatMap)
      })
  }

  onClickData(seat) {
    if(this.state.selectedSeats.length > 7){
        alert("Cannot select more than 8 seats")
    }
    else{
      if(this.state.selectedSeats.indexOf(seat) > -1 ) {
        this.setState({
          availableSeats: this.state.availableSeats.concat(seat),
          selectedSeats: this.state.selectedSeats.filter(res => res !== seat)
        })
      } else {
        this.setState({
          selectedSeats: this.state.selectedSeats.concat(seat),
          availableSeats: this.state.availableSeats.filter(res => res !== seat)
        })
      }
    }
  }

  clearSelection() {
    this.setState({
      selectedSeats: []
    })
  }

  bookSeat() {
    if(this.state.selectedSeats.length === 0){
      alert("Please select a seat!")
    }
    else{
      alert("Seat Booked Successfully!")
    }
  }

  togglePopup() {
    this.setState({
      showPopup: !this.state.showPopup
    });
  }
  reserved(seat){
    alert(seat.rowName+seat.number+" is already booked! select another seat.")
  }

  render() {
    return (
        <div>
            <div className='container'>
              <div class="row">
                <div class="col-sm-7">
                  <div class='screen'>
                    <center>SCREEN</center>
                    <hr/> 
                  </div>
        {   
        Object.keys(this.state.rowToSeatMap).map(key => (
          
              <div class="row row-inner">
                 <div className='seat-row'>
                  {key}
                </div>
                { 
                    this.state.rowToSeatMap[key].map((seat) => (

                    seat.status === "AVAILABLE" ?
                    <div className={this.state.selectedSeats.indexOf(seat) > -1? 'seat-selected': 'seat-available'} key={seat.seatId} onClick = {e => this.onClickData(seat)}>
                    {seat.rowName}{seat.number} </div>
                    :
                    <div className='seat-occupied' key={seat.seatId} onClick = {e => this.reserved(seat)}>
                    {seat.rowName}{seat.number} </div>
                  
                    ))
                }
                </div>
        ))
       }
        <div class='legend'>
          <hr/>
          <div className='row row-inner'>
            <div className='seat-available'/> AVAILABLE
          </div>
          <div className='row row-inner'>
            <div className='seat-selected'/> SELECTED
          </div>
          <div className='row row-inner'>
            <div className='seat-occupied'/> OCCUPIED
          </div>
       </div>
        </div>
        <div  class="col-sm-5">
          <SelectedList selected = { this.state.selectedSeats } />
          <hr/>
          <button disabled={this.state.selectedSeats.length ===0} onClick={this.clearSelection.bind(this)}>
            CLEAR
          </button>
          <button disabled={this.state.selectedSeats.length ===0} onClick={this.togglePopup.bind(this)}>
            BOOK
          </button>
         
          
          {this.state.showPopup ? 
            <Popup
              selected= {this.state.selectedSeats }
              closePopup={this.togglePopup.bind(this)}
            />
            : null
          }
              </div>
            </div> 
          </div>
        </div>
    )
  }
}
class SelectedList extends React.Component {
  render() {
    return(
      <div className="right">
          <h4> WHERE TO SIT? </h4>
          
            { this.props.selected.map(seat => <div className='seat-selected' key={seat} >{seat.rowName}{seat.number}</div>) }
          
          <hr/>
          <div >
            <div className='ticket-details'> <i class="fa fa-ticket" aria-hidden="true" ></i> x{this.props.selected.length}</div>
            <div className='ticket-details'> <i class="fa fa-usd" aria-hidden="true"></i> {this.props.selected.reduce((acc,seat) =>  acc = acc + seat.price , 0 )} </div>
          </div>
      </div>
    )
  }
}

class Popup extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      name: '',
      email:'',
      mobileNumber:''
    };

    this.handleChange = this.handleChange.bind(this);
    this.handleSubmit = this.handleSubmit.bind(this);
  }
  handleChange(event) {
    let nam = event.target.name;
    let val = event.target.value;
    this.setState({[nam]: val});
  }

  handleSubmit(event) {
    let req = {user: this.state, seats: this.props.selected}
    http.post('/book', req);
       console.log(req, this.state);
    alert('Booked '+this.props.selected.length+' tickets by ' + this.state.name);
    event.preventDefault();
  }

  render() {
    return (
      <div className='popup'>
        <div className='popup_inner'>
          <h3>Enter Details</h3>
          <form onSubmit={this.handleSubmit}>
          <div className="form-group">
         
            <input type="text" name="name" minlength="4" placeholder="Name" required onChange={this.handleChange}/>
        
          </div>
          <div className="form-group">
          
            <input type="email" name="email" placeholder="Email" required onChange={this.handleChange} />
         
          </div>
          <div className="form-group">
          
            <input type="number"
            name="mobileNumber" filter="[^0-9]" minlength="10" maxLength="10" placeholder="Mobile No" required onChange={this.handleChange} />
         
         </div>
         <div className="form-group">
          <input className="btn btn-primary" type="submit" value="BOOK" />
         </div>
         <div className="form-group">
          <input className="btn btn-primary" onClick={this.props.closePopup} value="BACK" />
         </div>

        </form>
        </div>
      </div>
    );
  }
}