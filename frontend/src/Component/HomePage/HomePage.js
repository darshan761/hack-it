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
    alert("already booked")
  }

  render() {
    return (
        <div>
            <div className='Container'>
        {   
        Object.keys(this.state.rowToSeatMap).map(key => (
            <div >
                { 
                    this.state.rowToSeatMap[key].map((seat) => (
                      seat.status === "AVAILABLE" ?
                    <div className={this.state.selectedSeats.indexOf(seat) > -1? 'seat-reserved': 'seat-available'} key={seat.seatId} onClick = {e => this.onClickData(seat)}>
                    {seat.rowName}{seat.number} </div>
                    :
                    
                    <div className='seat-booked' key={seat.seatId} onClick = {e => this.reserved(seat)}>
                    {seat.rowName}{seat.number} </div>
                  
                    ))
                }
            </div>
        ))
       }
        </div>
        <SelectedList selected = { this.state.selectedSeats } />
        <button onClick={this.clearSelection.bind(this)}>
          Clear
        </button>
        <button onClick={this.togglePopup.bind(this)}>
          Book
        </button>
        {this.state.showPopup ? 
          <Popup
            text='Confirm Details'
            selected= {this.state.selectedSeats }
            closePopup={this.togglePopup.bind(this)}
          />
          : null
        }
        </div> 
    )
  }
}
class SelectedList extends React.Component {
  render() {
    return(
      <div className="right">
        <h4>Selected Seats: ({this.props.selected.length})</h4>
        <ul>
          { this.props.selected.map(seat => <li key={seat} >{seat.rowName}{seat.number}</li>) }
        </ul>
        <h4> Total Price: {this.props.selected.reduce((acc,seat) =>  acc = acc + seat.price , 0 )}</h4>
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
          <h1>{this.props.text}</h1>
          <form onSubmit={this.handleSubmit}>
          <label>
            Name:
            <input type="text" name="name" minlength="4" required onChange={this.handleChange}/>
          </label>
          <label>
            Email:
            <input type="email" name="email" required onChange={this.handleChange} />
          </label>
          <label>
            Mobile:
            <input type="number"
            name="mobileNumber" required onChange={this.handleChange} />
          </label>
          <input type="submit" value="Submit" />
        </form>
        <button onClick={this.props.closePopup}>BACK</button>
        </div>
      </div>
    );
  }
}