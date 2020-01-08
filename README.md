# cool
1.验证lua脚本的原子性
2.本例主要是验证下redis的哨兵模式，一主两从三哨兵
  当主节点宕机后，redis哨兵会选举对应的slave成为新的master节点，老master启动后，成为新slave
  与springboot整合后，读写操作还是在master上面，没法做动态的读写分离
  网上很多例子都是固定的master和slave做的读写分离，没办法再哨兵模式下进行动态的读写分离，该问题有待后续验证
