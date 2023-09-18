import { createRouter, createWebHistory } from "vue-router";
import TodoList from "./components/TodoList.vue";
import AddTodo from "./components/AddTodo.vue";

const router = createRouter({
  history: createWebHistory(),
  routes: [
    { path: '/', name: 'Home', component: TodoList },
    { path: '/new', name: 'Add Todo', component: AddTodo },
  ]
})

export default router
