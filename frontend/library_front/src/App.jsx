import './App.css'

import React from "react";
import {BrowserRouter as Router, Route, Routes} from "react-router-dom";
import UserInfo from "./components/UserInfo";
import Start from "./components/Start";
import PatientAppointments from './components/PatientAppointments';

function App() {

    return (
        <Router>
            <div className="App">
                <div>
                    <img src="/icon-medicine.png" className="logo" alt=" logo"/>
                    <img src="/icon-doctor.png" className="logo doctor" alt="doctor logo"/>
                </div>
                <h3 className="subtitle">Приватна поліклініка</h3>
                <h1 class="title"> ProfiMed</h1>

                <Routes>
                    <Route path="/" element={<Start/>}/>
                    <Route path="/info" element={<UserInfo/>}/>
                    <Route path="/patient/:id" element={<PatientAppointments/>}/>
                </Routes>
            </div>
        </Router>
    );
}

export default App;
