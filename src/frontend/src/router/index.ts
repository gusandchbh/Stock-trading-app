import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/Home.vue'
import Login from "@/views/Login.vue";

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: HomeView
    },
    {
      path: '/login',
      name: 'login',
      component: Login
    }
  ]
})

export default router
