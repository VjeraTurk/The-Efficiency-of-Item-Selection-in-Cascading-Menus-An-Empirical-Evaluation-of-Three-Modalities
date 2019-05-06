setwd("C:/Users/admin/git/Scroll/rezultati/SMT/avg")
files <- list.files()
f<-list()
header<-list("id","S1","S2","S3","M1","M2","M3","T1","T2","T3")

#data=data.frame()
#names(data)=columns

#file.remove("Avg.csv")

write.table(header, file="Avg.csv",col.names = F, row.names = F,sep="," )

for (i in 1:length(files)) {
  f[[i]] <- read.csv(paste0(files[i]), header = F, sep = ",")
  write.table(f[[i]], file="Avg.csv", append=T,col.names = F, row.names = F, sep=",")
}
