<template>
    <div class="fondo-login">
        <div class="contenedor-login">
                <div class="div-login-form">
                    <form action="">
                        <h1>Inicia sesión</h1>
                        <div class="div-inputs-login">
                            <label>Email</label>
                            <input type="email" placeholder="Ej: email@gmail.com" required>
                            <label>Password</label>
                            <input type="password" placeholder="Contraseña" required>
                            <p>¿No estas registrado? <router-link to="/register">Registrate</Router-link></p>
                        </div>
                        <div class="div-button-login">
                            <button type="submit">Iniciar sesión</button>
                        </div>
                    </form>
                </div>
        </div>  
    </div>
</template>

<script setup>

</script>

<style scoped>
.fondo-login {
    display: flex;
    justify-content: center;
    align-items: center;
    height: 100%;
    width: 100%;
    background-image: url('../../assets/0bdf1db1939da634b85d6dccf27c35fd.jpg');
    background-size: cover;
    background-position: center;
}

.contenedor-login {
    display: flex;
    justify-content: center;
    align-items: center;
    height: 75%;
    width: 35%;
    background-color:white;
    border-radius: 1rem;
    border: solid 1px white;
    box-shadow: rgba(0, 0, 0, 0.35) 0px 5px 15px;
}

.div-login-form {
    height: 100%;
    width: 100%;
    right: 0;
    border-radius: 1rem;
    margin: 1rem;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    font-family: "Open Sans", sans-serif;
}

.div-login-form h1 {
    font-size: 2.5rem;
    font-weight: bold;
    margin-bottom: 1rem;
    height: 20%;
    width: 100%;
    display: flex;
    justify-content: center;
    align-items: center;
    color: #721B65;
}

.div-login-form form {
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    width: 100%;
    height: 100%;
}


.div-button-login {
    display: flex;
    justify-content: center;
    align-items: center;
    width: 100%;
    height: 20%;
}

.div-button-login button {
    width: 85%;
    height: 3rem;
    padding: 1rem;
    background-color: #721B65;
    color: white;
    border-radius: 0.5rem;
    border: none;
    cursor: pointer;
    font-size: 1rem;
    font-weight: bold;
    justify-content: center;
    align-items: center;
    display: flex;
}

.div-button-login button:hover {
    background-color: white;
    color: #721B65;
    border: solid 2px #721B65;
}

.div-inputs-login {
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    color: #721B65;
    width: 100%;
    height: 50%;
}

.div-inputs-login input{
    width: 80%;
    height: 2rem;
    margin: 1rem;
    padding: 1rem;
    font-size: 1rem;
    background-color: white;
    color: rgb(45, 44, 44);
    border-radius: 0.5rem;
    border: solid 1px #721B65;
}

.div-inputs-login input:focus{
    outline: none;
    border: solid 1px #721B65;
    box-shadow: 0 0 2.5px #721B65;
}

.div-inputs-login label{
    font-size: 1rem;
    font-weight: bold;
    margin: 0.5rem;
}

</style>