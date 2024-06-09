import React, {useState} from "react";
import {useNavigate} from "react-router-dom";
import './AuthForm.css'

export function AuthForm({mode}) {
    const [errorMessage, setErrorMessage] = useState("");
    const navigate = useNavigate();

    const handleSubmit = (event) => {
        event.preventDefault();

        const formData = new FormData(event.target);
        const id = formData.get("id");
        const password = formData.get("password");

        fetch("http://localhost:3030/hospital/login", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify({mode: mode, id: id, password: password}),
        })
            .then((response) => response.json())
            .then((result) => {
                console.log(result)
                if (result === 0) {
                    setErrorMessage("Неправильний ID або пароль");
                } else {
                    if (mode === 0) {
                        navigate("/info", {state: {user: result}});
                    } else navigate(`/patient/${id}`, {state: {user: result, id: id}});
                }
            })
            .catch((error) => {
                console.error("Помилка під час виконання запиту:", error);
            });
    };

    return (
        <div>
            <form onSubmit={handleSubmit}>
                <div className="input-container">
                    <label className="subtitle" htmlFor="id">
                        ID:
                    </label>
                    <input type="text" id="id" name="id"/>
                </div>

                <div className="input-container">
                    <label className="subtitle" htmlFor="password">
                        Пароль:
                    </label>
                    <input type="password" id="password" name="password"/>
                </div>

                <button className="button" type="submit">
                    Увійти
                </button>
            </form>
            {errorMessage && <p>{errorMessage}</p>}
        </div>
    );
}

export default AuthForm