import request from '../utils/request'

export function add(type, title, content) {
  return request({
    url: '/v1/article',
    method: 'post',
    data: {
      type, title, content
    }
  })
}

export function del(id) {
  return request({
    url: '/v1/article/' + id,
    method: 'delete'
  })
}

export function update(id, type, title, content) {
  return request({
    url: '/v1/article/' + id,
    method: 'put',
    data: {
      type, title, content
    }
  })
}

export function getById(id) {
  return request({
    url: '/v1/article/' + id,
    method: 'get'
  })
}

export function getPagination(type, keyword, pageNo, pageSize) {
  const p = new URLSearchParams()
  if (type != null && type.length > 0) p.append('type', type)
  if (keyword != null && keyword.length > 0) p.append('keyword', keyword)

  p.append('pageNo', pageNo)
  p.append('pageSize', pageSize)

  return request({
    url: '/v1/article/search',
    method: 'get',
    params: p
  })
}
