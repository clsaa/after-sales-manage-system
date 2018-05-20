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

export function update(id, name, type, age, birthday, gender, mobile, email, wechat, qq, note) {
  return request({
    url: '/v1/customer/' + id,
    method: 'put',
    data: {
      name, type, age, birthday, gender, mobile, email, wechat, qq, note
    }
  })
}
