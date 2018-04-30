import request from '../utils/request'

export function getPagination() {
  return request({
    url: '/v1/customer/pagination',
    method: 'get'
  })
}
