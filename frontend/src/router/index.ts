import { createRouter, createWebHistory } from "vue-router";
import HomeView from "@/views/HomeView.vue";
import VotingView from "@/views/VotingView.vue";
import AboutView from  "@/views/AboutView.vue";
import ThankyouView from "@/views/ThankyouView.vue";


const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: "/",
      name: "home",
      component: HomeView,
    },
    {
      path: "/about",
      name: "about",
      component: AboutView,
    },
    {
      path: "/voting",
      name: "voting",
      component: VotingView,
    },
    {
      path: "/thankyou",
      name: "thankyou",
      component: ThankyouView,
    }
  ],
});

export default router;