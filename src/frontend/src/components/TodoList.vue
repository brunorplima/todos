<script setup>
import { onMounted, ref } from "vue";
import { getAllTodos } from "../api/todos.js";
import Todo from "../entities/Todo.js";
import TodoCard from "./TodoCard.vue";

const todos = ref([])

onMounted(() => {
  async function fetchData() {
    const data = await getAllTodos()
    todos.value = Todo.getList(data)
  }
  fetchData()
})
</script>

<template>
  <div id="todos" class="d-flex flex-column align-items-sm-center">
    <ul id="todo-list" class="list-unstyled w-100">
      <li
        v-for="(todo, index) in todos"
        :key="todo.id"
        class="card w-100"
        :class="index !== todos.length - 1 && 'mb-3'"
      >
        <TodoCard :todo="todo" />
      </li>
    </ul>
  </div>
</template>

<style scoped>
#todos {
  margin: 0 20px;
}
#todo-list {
  max-width: 700px;
}
</style>
