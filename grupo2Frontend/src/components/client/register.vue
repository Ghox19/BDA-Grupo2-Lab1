
<script setup>
import { ref } from 'vue';
import { useRouter } from 'vue-router';
import { createClient } from "../../Services/UserService";

  const router = useRouter();
  const name = ref('')
  const email = ref('')
  const password = ref('')
  const telefono = ref('')
  const direccion = ref('')
  const password2 = ref('')

  const register = async () => {
    try {
      console.log('register')
      if(name.value === '' || email.value === '' || password.value === '' || telefono.value === '' || direccion.value === ''){
        alert('Todos los campos son obligatorios')
        return
      }
      if(password2.value !== password.value){
        alert('Las contraseñas no coinciden')
        return
      }
      if(password.value.length < 4){
        alert('La contraseña debe tener al menos 4 caracteres')
        return
      }
      const data = {
        nombre: name.value,
        email: email.value,
        clave: password.value,
        telefono: telefono.value,
        direccion: direccion.value,
      }
      console.log(data)
      const response = await createClient(data);
      if(response.status === 201){
        alert('Usuario creado correctamente')
        router.push({ name: 'login' });
      } else {
        alert('Error al crear el usuario')
        console.log(response)
      }
    } catch (error) {
      console.log(error)
    }
  }
</script>

<template>
  <div class="fondo">
    <div class="conteiner">
      <h2 class="title">Registro</h2>
      <form @submit.prevent="register">
        <div class="fila">
          <div class="form-group">
            <label class="label" for="name">Name</label>
            <input type="text" v-model="name" class="form-control" id="name" placeholder="Ej: Juan Torres" @input="name = name.replace(/\d/g, '')">
          </div>
          <div class="form-group">
            <label class="label" for="email">Email address</label>
            <input type="email" v-model="email" class="form-control" id="email" placeholder="Ej: ejemplo@dominio.com">
          </div>
        </div>
        
        <div class="fila">
          <div class="form-group">
            <label class="label" for="direccion">Dirección</label>
            <input type="text" v-model="direccion" class="form-control" id="direccion" placeholder="Ej: Los Manzanos 520">
          </div>
          <div class="form-group">
            <label class="label" for="telefono">Teléfono</label>
            <input type="tel" v-model="telefono" class="form-control" id="telefono" placeholder="Ej: 955663322" @input="telefono = telefono.replace(/\D/g, '')">
          </div>
        </div>
        <div class="fila">
          <div class="form-group">
            <label class="label" for="password">Contraseña</label>
            <input type="password" v-model="password" class="form-control" id="password" placeholder="Password">
          </div>
          <div class="form-group">
            <label class="label" for="password">Repita La Contraseña</label>
            <input type="password" v-model="password2" class="form-control" id="password2" placeholder="Password">
          </div>
        </div class="div-btn-register">
            <button type="submit" class="btn-btn-primary">Registrarse</button>
      </form>
    </div>
  </div>
</template>

<style scoped>
.fondo{
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-image: url('/src/assets/0bdf1db1939da634b85d6dccf27c35fd.jpg');
  background-size: cover;
  display: flex;
  align-items: center;
  justify-content: center;
}
.conteiner{
  margin: 0 auto;
  width: 600px;
  padding: 40px;
  background: white;
  box-sizing: border-box;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  border-radius: 1rem;
}
.title{
  text-align: center;
  color: #721B65;
  margin-bottom: 20px;
}
.form-group{
  position: relative;
  margin-bottom: 25px;
}
.form-control{
  width: 100%;
  height: 35px;
  padding: 5px;
  font-size: 15px;
  background: white;
  color: black;
}
.label{
  display: block;
  font-size: 15px;
  color: #721B65;
  font-weight: bold;
  margin-bottom: 5px;
}
.fila{
  display: flex;
  gap: 30px;
  margin-left: 10px;
  margin-right: 10px;
}
.btn-btn-primary{
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  width: 95%;
  height: 2rem;
  margin: 1rem;
  padding: 1rem;
  font-size: 1rem;
  background-color: white;
  color: rgb(45, 44, 44);
  border-radius: 0.5rem;
  border: solid 1px #721B65;
}
</style>
