import request from '../utils/request'

export function getPagination(type, important, urgent, status, pageNo, pageSize) {
  const p = new URLSearchParams()
  if (type != null && type.length > 0) p.append('type', type)
  if (important != null && important.length > 0) p.append('important', important)
  if (urgent != null && urgent.length > 0) p.append('urgent', urgent)
  if (status != null && status.length > 0) p.append('status', status)

  p.append('pageNo', pageNo)
  p.append('pageSize', pageSize)

  return request({
    url: '/v1/plan/pagination',
    method: 'get',
    params: p
  })
}

export function add(type, important, urgent, note, beginTime, endTime) {
  return request({
    url: '/v1/plan',
    method: 'post',
    data: {
      type,
      important,
      urgent,
      note,
      beginTime,
      endTime
    }
  })
}

export function del(id) {
  return request({
    url: '/v1/plan/' + id,
    method: 'delete'
  })
}

export function finish(id) {
  return request({
    url: '/v1/plan/' + id,
    method: 'put'
  })
}
