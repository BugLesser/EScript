// 递归计算斐波那契数列
function fib1(n) {
	if(n <= 2) {
		return 1
	}
	return fib1(n - 1) + fib1(n - 2)
}

// 非递归计算斐波那契数列
function fib2(n) {
	var a = 1
	var b = 1
	var c = 1
	var t = 0
	while(c < n) {
		t = a
		a = b
		b = b + t
		c = c + 1
	}
	return a
}

// 打印水仙花数
function narcissisticNumber() {
	var a
	var b
	var c
	var i = 100
	while(1 == 1) {
		if(i >= 1000) {
			break
		}
		a = i / 100
		b = (i / 10) % 10
		c = i % 10
		if(i == (a * a * a) + (b * b * b) + (c * c * c)) {
			print(i)
		}
		i = i + 1
	}
	return 0 // 无论有没有返回值, return 0是必须的, 这是一个BUG
}

// main函数是程序的入口
function main() {
	print(fib1(20))
	print(fib2(20))
	narcissisticNumber()
	return 0
}