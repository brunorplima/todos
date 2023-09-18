<script setup>
import { ref } from "vue";

const { id, value, label } = defineProps([
  'id',
  'value',
  'label'
])
const emit = defineEmits(['changeValue'])
const inputValue = ref(value)

function changeValue() {
  emit('changeValue', inputValue)
}
</script>

<template>
  <div class="form-group">
    <label :for="id" class="custom-label">{{ label }}</label>
    <input :id="id" class="form-control" type="text" v-model="inputValue" @input="changeValue" />
  </div>
</template>