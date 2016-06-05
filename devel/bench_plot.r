par(family="Helvetica")

sprintff <- c(66667, 2124, 567537, 784806, 1090798, 4431308)
stringformat <- c(5102, 5692, 32025, 80811, 217170, 457254)
x <- c(1, 10, 1000, 10000, 100000, 1000000)

plot(x, sprintff, xlab = "n", ylab = "Rate/s", main = "Benchmark", log = "x", type = "n")
lines(x, sprintff, col = "red", lwd=2)
lines(x, stringformat, col = "blue", lwd=2)
legend("topleft", c("Sprint#ff()", "String.format()"), lty = 1, col = cols, lwd=2, cex=1.3, bty = "n")

