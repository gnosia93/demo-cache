#!/bin/sh

host=cachedemo-api-alb-177367678.ap-northeast-1.elb.amazonaws.com
target=http://$host/order//event-add
ab -l -p order-payload.json -T 'application/json;charset=utf-8' -e order-payload.csv -c 150 -n 3000 $target
