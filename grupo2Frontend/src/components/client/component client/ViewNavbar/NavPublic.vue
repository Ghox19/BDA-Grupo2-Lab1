<template>
    <div class="content-login">
        <router-link to="/login">
                <button class="btn-login">Ir al Login</button>
        </router-link>
    </div>
</template>

<style scoped>
.content-login {
    display: flex;
    justify-content: flex-end;
    align-items: center;
    padding: 1rem;
    height: 100%;
    width: 100%;
}
.btn-login {
    padding: 1rem;
    background: linear-gradient(to left, white 50%, #4944b8 50%);
    background-size: 200% 100%;
    background-position: right bottom;
    color: #4944b8;
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