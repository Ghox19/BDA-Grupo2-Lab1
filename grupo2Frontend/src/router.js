import { createRouter, createWebHistory } from 'vue-router';
import Login from './components/client/login.vue';
import register from './components/client/register.vue';

const routes = [
    {
        path: '/', redirect: '/register'
    },
    {
        path: '/login',
        name: 'login',
        component: Login,
    },
    {
        path: '/register',
        name: 'register',
        component: register,
    }
];

const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes,
});

export default router;