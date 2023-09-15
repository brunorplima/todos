<script setup>
import { ref } from "vue";

const { todo } = defineProps(['todo'])
const open = ref(false)
</script>

<template>
  <div class="card-body d-flex gap-2">
    <div class="">
      <input class="form-check-input" type="checkbox" :id="`todo-${todo.id}`">
    </div>

    <div class="flex-grow-1 user-select-none" @click="open = !open">
      <div id="todoTitle" class="d-flex gap-2">
        <h6 class="form-check-label card-title">{{ todo.title }}</h6>
        <fa-icon v-if="open" icon="chevron-up" size="2xs" class="chevron-icon" />
        <fa-icon v-else icon="chevron-down" size="2xs" class="chevron-icon" />
      </div>
      <div v-if="open">
        {{ todo.description }}
      </div>
    </div>

    <div>
      <fa-icon icon="pen" class="pen-icon" />
    </div>
  </div>
</template>

<style scoped>
#todoTitle {
  cursor: pointer;
  transition: .3s;
}
#todoTitle:hover {
  color: var(--blue);
}
.pen-icon {
  color: var(--blue);
  padding: 6px;
  cursor: pointer;
  transition: .3s;
}
.pen-icon:hover {
  color: var(--blue-light);
}
.chevron-icon {
  transform: translateY(5px);
}
</style>