<script setup>
import TextInput from "./form/TextInput.vue";
import TextArea from "./form/TextArea.vue";

const { updatingTodo } = defineProps({
  updatingTodo: {
    type: Object,
    required: true
  }
})

function changeTitle(value) {
  updatingTodo.title = value
}

function changeDescription(value) {
  updatingTodo.description = value
}
</script>

<template>
  <div class="flex-grow-1">
    <div class="d-flex flex-column gap-2">
      <TextInput id="todo-title" label="Title" :value="updatingTodo.title" @changeValue="changeTitle" />
      <TextArea id="todo-description" label="Description" :value="updatingTodo.description" @changeValue="changeDescription" />
    </div>
  </div>
</template>
