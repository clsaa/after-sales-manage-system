import request from '../utils/request'

export function add(name, mobile, title, type, description) {
  return request({
    url: '/v1/workorder',
    method: 'post',
    data: {
      name, mobile, title, type, description
    }
  })
}

export function getPagination(type, status, beginTime, endTime, keyword, pageNo, pageSize) {
  const p = new URLSearchParams()
  if (type != null && type.length > 0) p.append('type', type)
  if (status != null && status.length > 0) p.append('status', status)
  if (beginTime != null) p.append('beginTime', beginTime)
  if (endTime != null) p.append('endTime', endTime)
  if (keyword != null && keyword.length > 0) p.append('keyword', keyword)

  p.append('pageNo', pageNo)
  p.append('pageSize', pageSize)

  return request({
    url: '/v1/workorder/pagination',
    method: 'get',
    params: p
  })
}

export function getCustomerWorkOrderList(type, status, beginTime, endTime, mobile) {
  const p = new URLSearchParams()
  if (type != null && type.length > 0) p.append('type', type)
  if (status != null && status.length > 0) p.append('status', status)
  if (beginTime != null) p.append('beginTime', beginTime)
  if (endTime != null) p.append('endTime', endTime)
  if (mobile != null && mobile.length > 0) p.append('mobile', mobile)

  return request({
    url: '/v1/customer/workorder/list',
    method: 'get',
    params: p
  })
}

export function getById(id) {
  return request({
    url: '/v1/workorder/' + id,
    method: 'get'
  })
}

export function addComment(workorderId, content, source) {
  return request({
    url: '/v1/workorder/' + workorderId + '/comment',
    method: 'post',
    data: {
      content: content,
      source: source
    }
  })
}

export function updateStatus(id, statusFrom, statusTo) {
  return request({
    url: '/v1/workorder/' + id + '/status',
    method: 'put',
    params: {
      statusFrom, statusTo
    }
  })
}
