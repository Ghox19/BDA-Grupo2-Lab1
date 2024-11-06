import { createRouter, createWebHistory } from 'vue-router';
import Home from './components/clients/home.vue';
import Login from './components/clients/login.vue';

const routes = [
    {
        path: '/', redirect: '/home'
    },
  {
    path: '/home',
    name: 'home',
    component: Home,
  },
  {
    path: '/login',
    name: 'login',
    component: Login,
  },
];

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes,
});

export default router;