<script setup>
import TextInput from "./form/TextInput.vue";
import TextArea from "./form/TextArea.vue";

const { updatingTodo } = defineProps(['updatingTodo'])

function changeTitle(value) {
  updatingTodo.title = value
}

function changeDescription(value) {
  updatingTodo.description = value
}
</script>

<template>
  <div class="flex-grow-1">
    <form class="d-flex flex-column gap-2">
      <TextInput id="todo-title" label="Title" :value="updatingTodo.title" @changeValue="changeTitle" />
      <TextArea id="todo-description" label="Description" :value="updatingTodo.description" @changeValue="changeDescription" />
    </form>
  </div>
</template>
