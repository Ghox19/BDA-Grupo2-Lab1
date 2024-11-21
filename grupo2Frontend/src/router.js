import { createRouter, createWebHistory } from 'vue-router';
import Home from './components/client/Home.vue';
import Register from './components/client/register.vue';
import Login from './components/client/Login.vue';

const routes = [
  {
    path: '/', redirect: '/home'
  },
  {
    path: '/home',
    name: 'home',
    component: Home
  },
  {
    path: '/login',
    name: 'login',
    component: Login
  },
  {
    path: '/register',
    name: 'register',
    component: Register
  }
];

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes,
});

export default router;