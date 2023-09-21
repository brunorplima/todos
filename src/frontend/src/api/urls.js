const BASE_URL = 'http://localhost:8080'
export const getTodosUrl = (id = '', suffix = null) =>
  `${BASE_URL}/api/todos/${id}${suffix ? `/${suffix}` : ''}`