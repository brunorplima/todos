const BASE_URL = 'http://localhost:8080'
export const getTodosUrl = (id = '') => `${BASE_URL}/api/todos/${id}`