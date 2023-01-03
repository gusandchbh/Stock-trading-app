import { createRouter, createWebHistory } from "vue-router";
import Register from "../views/Register.vue";
import Login from "@/views/Login.vue";
import { useAuthStore } from "@/stores/auth";
import UserPage from "@/views/UserPage.vue";

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: "/",
      name: "home",
      component: Register,
    },
    {
      path: "/login",
      name: "login",
      component: Login,
    },
    {
      path: "/userpage",
      name: "userpage",
      component: UserPage,
    },
  ],
});

router.beforeEach(async (to) => {
  // redirect to login page if not logged in and trying to access a restricted page
  const publicPages = ["/login", "/"];
  const authRequired = !publicPages.includes(to.path);
  const token = localStorage.getItem("token");
  const auth = useAuthStore();

  if (authRequired && !token) {
    auth.returnUrl = to.fullPath;
    return "/login";
  }
});
export default router;
