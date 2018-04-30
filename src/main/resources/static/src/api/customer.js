import request from '../utils/request'

export function getPagination(type, gender, keyword, pageNo, pageSize) {
  const p = new URLSearchParams()
  if (type != null && type.length > 0) p.append('type', type)
  if (gender != null && gender.length > 0) p.append('gender', gender)
  if (keyword != null && keyword.length > 0) p.append('keyword', keyword)

  p.append('pageNo', pageNo)
  p.append('pageSize', pageSize)

  return request({
    url: '/v1/customer/pagination',
    method: 'get',
    params: p
  })
}

export function getById(id) {
  return request({
    url: '/v1/customer/' + id,
    method: 'get'
  })
}

export function del(id) {
  return request({
    url: '/v1/customer/' + id,
    method: 'delete'
  })
}
export function delBatch(ids) {
  let idsStr = ''
  ids.forEach(id => {
    idsStr += id + ','
  })
  console.log(idsStr)
  console.log(idsStr.length)
  idsStr = idsStr.substr(0, idsStr.length - 1)
  return request({
    url: '/v1/customer/batch',
    method: 'delete',
    params: {
      'ids': idsStr
    }
  })
}

export function add(name, type, age, birthday, gender, mobile, email, wechat, qq, note) {
  return request({
    url: '/v1/customer',
    method: 'post',
    data: {
      name, type, age, birthday, gender, mobile, email, wechat, qq, note
    }
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
