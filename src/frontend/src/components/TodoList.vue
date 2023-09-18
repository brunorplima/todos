<script setup>
import { onMounted, ref } from "vue";
import { getAllTodos } from "../api/todos.js";
import TodoCard from "./TodoCard.vue";

const todos = ref([])

onMounted(() => {
  const fetchData = async () => todos.value = await getAllTodos()
  fetchData()
})

function applyUpdateChangesToTodo(data) {
  todos.value = todos.value.map(t => {
    if (t.id === data.id) return data
    return t
  })
}

function applyDeleteChangesToTodos(id) {
  todos.value = todos.value.filter(t => t.id !== id)
}
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
        <TodoCard
          :todo="todo"
          @updateTodo="applyUpdateChangesToTodo"
          @deleteTodo="applyDeleteChangesToTodos"
        />
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
