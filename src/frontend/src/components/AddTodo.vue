<script setup>
import { computed, ref } from "vue";
import TodoEdit from "./TodoEdit.vue";
import { createTodo } from "../api/todos.js";
import { useRouter } from "vue-router";

const todo = ref({
  title: '',
  description: ''
})

const disabled = computed(() => !todo.value.title)
const router = useRouter()

async function addTodo() {
  await createTodo(todo.value)
  await router.push('/')
}
</script>

<template>
  <div class="px-3 px-sm-5">
    <form @submit.prevent="addTodo">
      <TodoEdit :updatingTodo="todo" />
      <div class="mt-4 w-100 d-flex justify-content-center">
        <button type="submit" class="btn btn-success" :disabled="disabled">Add</button>
      </div>
    </form>
  </div>
</template>
