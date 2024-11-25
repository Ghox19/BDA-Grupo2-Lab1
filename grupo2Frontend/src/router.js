import { createRouter, createWebHistory } from 'vue-router';
import Home from './components/client/Home.vue';
import Register from './components/client/register.vue';
import Login from './components/client/login.vue';
import allProducts from './components/client/component client/allProducts.vue';
import Product from './components/client/component client/product.vue';
import ListOrder from "./components/client/component client/ListOrder.vue";
import Order from './components/client/component client/ViewNavbar/orderDetails.vue';
import Log from './components/auditoria/Log.vue';
import Ranked from './components/ranked/Ranked.vue';
import { auth } from './Services/authentication';


const routes = [
  {
    path: '/', redirect: { name: 'allproducts', params: { id: 1 } }
  },
  {
    path: '/home',
    name: 'home',
    component: Home,
    children: [
      {
        path: 'allproducts/:id',
        name: 'allproducts',
        component: allProducts
      },
      {
        path: 'product/:id',
        name: 'product',
        component: Product
      },
      {
        path: 'ListOrder',
        name: 'ListOrder',
        component: ListOrder,
        beforeEnter: auth
      },
      {
        path: 'order/:id',
        name: 'order',
        component: Order,
        beforeEnter: auth
      }
    ]
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
  },
  {
    path: '/log',
    name: 'log',
    component: Log
  },
  {
    path: '/ranked',
    name: 'ranked',
    component: Ranked
  }
];

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes,
});

export default router;