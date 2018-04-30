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

export function del(id) {
  return request({
    url: '/v1/customer/' + id,
    method: 'delete'
  })
}
