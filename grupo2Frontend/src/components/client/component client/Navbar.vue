<script>

</script>

<template>
    <div class="navbar-container">
        <div class="content">
            <h1>Ecommerce</h1>
            <router-link to="/login">
                <button class="btn-login">Ir al Login</button>
            </router-link>
        </div>
    </div>
</template>

<style scoped>
.navbar-container {
    display: flex;
    justify-content: center;
    align-items: center;
    height: 10vh;
    width: 100%;
    background-color: #721B65;
    color: white;
    font-family: "Open Sans", sans-serif;
}

.content {
    display: flex;
    justify-content: space-between;
    align-items: center;
    width: 100%;
    margin: 0 2rem;
}


.btn-login {
    padding: 1rem;
    background: linear-gradient(to left, white 50%, #721B65 50%);
    background-size: 200% 100%;
    background-position: right bottom;
    color: #721B65;
    border-radius: 0.5rem;
    border: 1px solid white;
    cursor: pointer;
    font-weight: bold;
    justify-content: center;
    align-items: center;
    display: flex;
    transition: background-position 0.4s ease, color 0.4s ease;
}

.btn-login:hover {
    background-position: left bottom;
    color: white;
}
</style>