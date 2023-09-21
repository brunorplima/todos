<script setup>
import { computed, onMounted, ref } from "vue";
import { getAllTodos } from "../api/todos.js";
import TodoCard from "./TodoCard.vue";

const todos = ref([])
const showCompleted = ref(true)

const completedTodos = computed(() => todos.value.filter(t => t.completed))
const notCompletedTodos = computed(() => todos.value.filter(t => !t.completed))

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
    <ul class="max-width list-unstyled w-100 mb-5">
      <li
        v-for="(todo, index) in notCompletedTodos"
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

    <div v-if="completedTodos.length" class="max-width w-100">
      <div class="d-flex justify-content-between">
        <h5>Completed</h5>
        <div
          class="text-muted btn btn-link"
          @click="showCompleted = !showCompleted"
        >
          {{ showCompleted ? 'hide' : 'show' }}
        </div>
      </div>
      <ul v-if="showCompleted" class="list-unstyled w-100">
        <li
          v-for="(todo, index) in completedTodos"
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
  </div>
</template>

<style scoped>
#todos {
  margin: 0 20px;
}
.max-width {
  max-width: 700px;
}
</style>
