<template>
  <div class="board-list-container">
    <Board
      v-for="board in boardStore.boards"
      :key="board.id"
      :board="board"
      :loginUserId="userStore.loginUser.id"
    />
    <div class="board-no-content" v-if="!boardStore.isExistBoard">
      <h3>등록된 게시글이 없습니다.</h3>
      <p><router-link :to="{name: 'boardRegist'}">여기</router-link>를 클릭해 게시글을 등록해보세요</p>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { useRoute } from 'vue-router';
import { useBoardStore } from '@/stores/board';
import { useUserStore } from '@/stores/user';
import Board from '@/components/board/Board.vue';

const boardStore = useBoardStore();
const userStore = useUserStore();

const boards = ref([]);
const route = useRoute();
const thunderId = route.params.thunderId;

onMounted(() => {
  boardStore.fetchBoards(thunderId);
});
</script>

<style scoped>
.board-list-container {
  display: flex;
  flex-direction: column;
  gap: 20px;
  padding: 20px;
}

.board-no-content {
  text-align: center;
  margin-top: 20px;
}
</style>
