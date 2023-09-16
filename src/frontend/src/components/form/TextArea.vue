<script setup>
import { ref } from "vue";

const { id, label, value } = defineProps([
  'id',
  'label',
  'value'
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
    <textarea :id="id" v-model="inputValue" class="form-control" @change="changeValue"></textarea>
  </div>
</template>